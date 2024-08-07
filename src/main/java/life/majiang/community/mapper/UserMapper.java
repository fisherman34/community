package life.majiang.community.mapper;

import java.util.List;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    long countByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    int deleteByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    User selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USERTABLE
     *
     * @mbg.generated Wed Jul 10 10:13:52 CST 2024
     */
    int updateByPrimaryKey(User record);
}