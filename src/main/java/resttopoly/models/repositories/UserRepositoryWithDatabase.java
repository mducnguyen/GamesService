package resttopoly.models.repositories;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import resttopoly.models.User;

import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 09/04/16
 */
public class UserRepositoryWithDatabase implements IUserRepository
{
    private Sql2o sql2o;

    /**
     * @param sql2o
     */
    public UserRepositoryWithDatabase(Sql2o sql2o)
    {
        this.sql2o = sql2o;
    }

    /**
     * @param name
     * @return User with username
     * null if user not found
     */
    @Override
    public User findUser(String name)
    {
        User user = null;
        try (Connection connection = sql2o.open()) {
            user = connection.createQuery("select * from users where name = :name")
                    .addParameter("name", name).executeAndFetchFirst(User.class);
        }

        return user;
    }

    /**
     *
     * @param user
     * @return user if Created
     * null if user already exist
     */
    @Override
    public User createUser(User user) throws CannotCreateException
    {
        try (Connection connection = sql2o.open()) {
             connection.createQuery("INSERT INTO users(name,uri) VALUES (:name,:uri)")
                    .addParameter("name", user.getName())
                     .addParameter("uri",user.getUri())
                    .executeUpdate();
        } catch (Sql2oException e){
            throw new CannotCreateException();
        }

        return user;
    }

    /**
     *
     * @param user
     * @return user if Created
     * null if user already exist
     */
    @Override
    public User updateUser(String oldName, User user) throws DatabaseConflictException
    {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("update users set name= :name1, uri= :uri where name=:name2")
                    .addParameter("name1", user.getName())
                    .addParameter("name2",oldName)
                    .addParameter("uri",user.getUri())
                    .executeUpdate();
        } catch (Sql2oException e){
            throw new DatabaseConflictException();
        }

        return user;
    }

    @Override
    public void deleteUser(User user)throws Sql2oException
    {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM Users where name= :name")
                    .addParameter("name",user.getName())
                    .executeUpdate();
        }
    }

    @Override
    public List<User> findAllUsers()
    {
        try(Connection connection = sql2o.open()){
            List<User> users = connection.createQuery("SELECT * FROM Users").executeAndFetch(User.class);
            return  users;
        }
    }
}
