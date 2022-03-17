package org.shellagent.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.shellagent.entity.Property;
import org.shellagent.entity.PropertyExample;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyMapper {
    int countByExample(PropertyExample example);

    int deleteByExample(PropertyExample example);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);
    List<String> selectValueByExample(PropertyExample example);

    int updateByExampleSelective(@Param("record") Property record, @Param("example") PropertyExample example);

    int updateByExample(@Param("record") Property record, @Param("example") PropertyExample example);
}
