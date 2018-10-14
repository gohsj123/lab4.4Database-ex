package my.edu.tarc.demoroom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.LinkedList;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    public List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE phone =:Phone")
    public User getUserByPhone(String Phone);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
