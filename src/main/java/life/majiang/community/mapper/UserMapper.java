package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * mapping between SQL databases and objects in Java
 * @author Sam
 * @create 2024-05-03 9:46 PM
 */
@Mapper
public interface UserMapper {

  /** This method is annotated with @Insert, which indicates that it will execute
   * an SQL INSERT statement. The SQL statement provided as the annotation’s value
   * will insert a new record into the UserTable with the values provided by the User
   * object’s properties. The #{} syntax is used for parameter substitution, where
   * MyBatis will replace these placeholders with the corresponding properties from
   * the User object passed to the method. MyBatis automatically uses the Java object’s
   * property names as placeholders within the SQL statement. When you pass a single
   * object parameter to a MyBatis mapper method, like the User object in this case,
   * MyBatis uses reflection to match the object’s field names with the named placeholders
   * in the annotated SQL statement.*/
  @Insert("insert into UserTable (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
  void insert(User user);

  /** This method is annotated with @Select, which indicates that it will execute an
   * SQL SELECT statement. The SQL statement provided will select a user from the user
   * table where the token column matches the method’s token parameter. The @Param
   * annotation is used to bind the method parameter to the SQL statement’s placeholder. */
  @Select("select * from UserTable where token = #{token}")
  User findByToken(@Param("token") String token);
}
