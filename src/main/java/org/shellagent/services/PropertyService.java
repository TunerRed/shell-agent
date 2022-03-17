package org.shellagent.services;

import org.shellagent.exception.MyException;
import org.shellagent.dto.ServerDTO;
import org.shellagent.entity.Property;
import org.shellagent.entity.PropertyExample;
import org.shellagent.mapper.PropertyMapper;
import org.shellagent.utils.Constant;
import org.shellagent.utils.EncUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PropertyService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PropertyMapper propertyMapper;

    /**
     * 使用获取符合条件的第一条记录.
     * */
    public Property getPropertyByKeys (String type, String key) {
        PropertyExample serverExample = new PropertyExample();
        serverExample.createCriteria().andTypeEqualTo(type).andKeyEqualTo(key);
        List<Property> list = propertyMapper.selectByExample(serverExample);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    public List<Property> getPropertyListByKeys (String type, String key) {
        PropertyExample serverExample = new PropertyExample();
        serverExample.createCriteria().andTypeEqualTo(type).andKeyEqualTo(key);
        List<Property> list = propertyMapper.selectByExample(serverExample);
        return (list==null||list.size()==0)?null:list;
    }

    /**
     * 获取目标值的集合.
     * @param type 目标记录的type
     * @param key 目标记录的key
     * @return 返回指定type和key的多条记录
     * */
    public List<String> getValueListByKeys (String type, String key) {
        PropertyExample serverExample = new PropertyExample();
        serverExample.createCriteria().andTypeEqualTo(type).andKeyEqualTo(key).andValIsNotNull();
        List<String> list = propertyMapper.selectValueByExample(serverExample);
        return (list==null||list.size()==0)?null:list;
    }

    public List<Property> getPropertyListByType (String type) {
        PropertyExample serverExample = new PropertyExample();
        serverExample.createCriteria().andTypeEqualTo(type).andValIsNotNull();
        List<Property> list = propertyMapper.selectByExample(serverExample);
        return (list==null||list.size()==0)?null:list;
    }

    /**
     * 从服务器的一套配置中根据字段获取自己想要的配置.
     * @param list 服务器配置
     * @param type 需要的字段，如user/pass/*path
     * */
    public String getValueByType(List<Property> list, String type) throws MyException {
        if (list == null || type == null)
            throw new MyException(Constant.ResultCode.NOT_FOUND,"服务器找不到任何配置");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType().equals(type))
                return list.get(i).getVal();
        }
        throw new MyException(Constant.ResultCode.NOT_FOUND,"服务器找不到对应配置："+type);
    }

    /**
     * 获取服务器信息.
     * 返回目标主机的一套配置，一个ip对应一套配置，若一个ip有多个用途/账户，它们对应同一套配置
     * @param serverIP 服务器地址
     * */
    public List<Property> getServerInfo (String serverIP) throws MyException {
        logger.info("---- 获取服务器配置 ["+serverIP+"] ----");
        PropertyExample serverExample = new PropertyExample();
        serverExample.createCriteria().andTypeEqualTo(Constant.PropertyType.IP).andValEqualTo(serverIP);
        List<Property> properties = propertyMapper.selectByExample(serverExample);
        if (properties == null || properties.size() != 1) {
            throw new MyException(Constant.ResultCode.NOT_FOUND,"找不到正确的服务器");
        }
        PropertyExample serverInfoExample = new PropertyExample();
        serverInfoExample.createCriteria().andKeyEqualTo(serverIP);
        List<Property> serverInfo = propertyMapper.selectByExample(serverInfoExample);
        return (serverInfo==null||serverInfo.isEmpty())?null:serverInfo;
    }

    public List<String> getServerListBySeqList (String key, List<Integer> seqList) {
        PropertyExample serverExample = new PropertyExample();
        serverExample.createCriteria().andTypeEqualTo(Constant.PropertyType.IP).andKeyEqualTo(key)
                .andSeqIn(seqList);
        List<Property> properties = propertyMapper.selectByExample(serverExample);
        if (properties == null)
            return null;
        List<String> list = new LinkedList<>();
        for (int i = 0; i < properties.size(); i++) {
            list.add(properties.get(i).getVal());
        }
        return list.isEmpty()?null:list;
    }

    public List<String> getAppPrefixList() {
        return joinWordList(getValueListByKeys(Constant.PropertyType.JAR_RENAME, Constant.PropertyKey.JAR_PREFIX));
    }
    public List<String> getAppSuffixList() {
        return joinWordList(getValueListByKeys(Constant.PropertyType.JAR_RENAME, Constant.PropertyKey.JAR_SUFFIX));
    }
    private List<String> joinWordList(List<String> list) {
        if (list == null)
            return null;
        List<String> retList = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            String[] wordList = list.get(i).split(" ");
            for (int j = 0; j < wordList.length; j++) {
                retList.add(wordList[j]);
            }
        }
        return retList;
    }

    public void insertNewServer(ServerDTO server) {
        PropertyExample example = new PropertyExample();
        example.setOrderByClause("seq");
        example.createCriteria().andTypeEqualTo(Constant.PropertyType.IP).andSeqIsNotNull();
        List<Property> ipList = propertyMapper.selectByExample(example);
        int seq = 0;
        if (ipList != null && ipList.size() > 0) {
            seq = ipList.get(ipList.size() - 1).getSeq() + 1;
        }
        Property ip = new Property(Constant.PropertyType.IP, server.getType(), server.getIp());
        ip.setSeq(seq);

        Property[] properties = new Property[6];
        properties[0]= new Property(Constant.PropertyType.USERNAME, server.getIp(), server.getUsername());
        properties[1]= new Property(Constant.PropertyType.PASSWORD, server.getIp(), EncUtil.encode(EncUtil.decodeUserPass(server.getPassword())));
        properties[2]= new Property(Constant.PropertyType.DEPLOY_PATH, server.getIp(), server.getDeployPath());
        properties[3]= new Property(Constant.PropertyType.BACKUP_PATH, server.getIp(), server.getBackupPath());
        if (server.getRunPath() != null && server.getRunPath().length() > 0)
            properties[4]= new Property(Constant.PropertyType.RUN_PATH, server.getIp(), server.getRunPath());
        if (server.getType().equalsIgnoreCase(Constant.PropertyKey.SERVICE))
            properties[5]= new Property(Constant.PropertyType.LOG_PATH, server.getIp(), server.getLogPath());
        for (int i = 0; i < properties.length && properties[i] != null; i++) {
            propertyMapper.insertSelective(properties[i]);
        }
        propertyMapper.insertSelective(ip);
    }

    public void updateServerInfo(ServerDTO server) throws MyException {
        List<Property> oldProperties = getServerInfo(server.getIp());
        for (int i = 0; i < oldProperties.size(); i++) {
            Property property = oldProperties.get(i);
            String val = "";
            switch (property.getType()) {
                case Constant.PropertyType.USERNAME:
                    val = server.getUsername();
                    break;
                case Constant.PropertyType.PASSWORD:
                    val = EncUtil.encode(EncUtil.decodeUserPass(server.getPassword()));
                    break;
                case Constant.PropertyType.DEPLOY_PATH:
                    val = server.getDeployPath();
                    break;
                case Constant.PropertyType.BACKUP_PATH:
                    val = server.getBackupPath();
                    break;
                case Constant.PropertyType.RUN_PATH:
                    val = server.getRunPath();
                    break;
                case Constant.PropertyType.LOG_PATH:
                    val = server.getLogPath();
                    break;
                default:
                    throw new MyException(Constant.ResultCode.INTERNAL_ERROR, "未知的配置类型");
            }
            if (property.getVal() == null || property.getVal().isEmpty() || !property.getVal().equals(val)) {
                property.setVal(val);
                PropertyExample example = new PropertyExample();
                example.createCriteria().andTypeEqualTo(property.getType()).andKeyEqualTo(property.getKey());
                propertyMapper.updateByExample(property, example);
            }
        }
    }
}
