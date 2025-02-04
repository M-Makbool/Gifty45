
import java.util.Calendar;
import java.util.List;
import java.sql.Date;
import java.sql.SQLException;

import gifty.*;
import gifty.dao.ItemQuery;
import gifty.dao.UserQuery;
import gifty.dto.Item;
import gifty.dto.User;
import gifty.dto.UserLogin;

public class Gifty45 {
    static User user1;
    static User user2;
    static Item item1;
    static Item item2;
    static Item retrievedItem;
    static List<Item> allItems;

     
    public static void main(String[] args) {
        testUser();
        testItem();
        try {
            UserQuery.addWish(user1, item1);
            UserQuery.addWish(user2, item2);
            UserQuery.getUser(user2);      
            UserQuery.deleteWish(user2, user2.getWishList().get(0));


        } catch (SQLException e) {
            e.printStackTrace();
        } 

    }




    public static void testUser() {

         user1 = new User(
                new UserLogin("hamada", "hamada"),
                "hamada@example.com",
                "Male",
                "123456789",
                new Date(2000, 01, 01));

        user2 = new User(
                new UserLogin("mahmoud", " mahmoud"),
                "mahmoud@example.com",
                "Female",
                "987654321",
                new Date(2001, 02, 02));

             // create new instance from userQuery :

        UserQuery userQuery = new UserQuery();
        try {
             // test adduser()
            userQuery.addUser(user2);
            userQuery.addUser(user1);
            // test getusers()
            for (UserLogin users : UserQuery.getUsers())
                System.out.println("users are " + users.getName());
        }
         catch (SQLException e) {

            e.printStackTrace();
        }
       System.out.println("adding users successfully ");
       
       
       
            //test addFreiend()
        try {
            userQuery.addFriend(user1, user2);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        System.out.println("adding friend");

        
    }


    public static void testItem() {

            // create two obj of items to test by

        item1 = new Item(0, "laptop", "devices", 50000, "AVAILABLE");
        item2 = new Item(0, "Iphone_11", "devices", 20000, "AVAILABLE");

            // create new instance from userQuery :

        ItemQuery itemQuery = new ItemQuery();
            //test addItem()
        try {
            itemQuery.addItem(item2);
            itemQuery.addItem(item1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("adding items successfully ");


            //test getItem()

            try {
            retrievedItem = itemQuery.getItem(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }  
            if (retrievedItem != null) {
                System.out.println(" Item Retrieved: " + retrievedItem.getItem_name());
            } else {
                System.out.println(" Item not found.");
            }



            // test getitems()
             System.out.println("\n Listing all available items:");
            try {
                allItems = ItemQuery.getItems();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!allItems.isEmpty()) {
                for (Item item : allItems) {
                    System.out.println(  item.getItem_name() + " - $" + item.getPrice());
                }
            } else {
                System.out.println(" No items found.");
            }



            //test  deleteItem
            System.out.println("\n Deleting an item:");
            try {
                itemQuery.deleteItem(item2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(" Item deleted successfully.");

    }

}









