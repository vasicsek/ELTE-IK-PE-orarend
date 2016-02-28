/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_crud;

import com.elte.tosz.controllers.RoomJpaController;
import com.elte.tosz.controllers.exceptions.NonexistentEntityException;
import com.elte.tosz.logic.OszDS;
import com.elte.tosz.logic.entities.Room;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cauchy
 */
public class RoomCRUDTest {
    
    private final OszDS ds;
    private final RoomJpaController ctrlRoom ;
    private Room room;
    
    public RoomCRUDTest() {
        ds = new OszDS();     
        ctrlRoom = ds.getCtrlRoom();        
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void crudTest() throws NonexistentEntityException, Exception{        
       
       roomCreation();
       roomRead();
       roomUpdate();
       roomDelete();
    }   
    
    public void roomCreation() {
        Room r = new Room();
        r.setBuilding(0);
        r.setCapacity(20);
        r.setFloor(1);        
        ctrlRoom.create(r);        
        List<Room> rooms = ctrlRoom.findRoomEntities();
        System.out.println("Rooms array length:" + rooms.size());
        int index = rooms.indexOf(r);
        System.out.println("Found index:"+index);
        assert( index > -1 );
        this.room = r;              
        
    }
    
    public void roomRead(){
        List<Room> rooms = ctrlRoom.findRoomEntities();
        rooms.forEach((room) -> {
            System.out.println(room);
        } );
        
        assert(!rooms.isEmpty());
        
        rooms.indexOf(rooms);
    }
    
    public void roomUpdate() throws Exception{
        room.setCapacity(150);
        ctrlRoom.edit(room);
        
    }
    public void roomDelete() throws NonexistentEntityException{
        System.out.println(room.toString());
        assertNull(room);
        ctrlRoom.destroy(room.getId());        
        room = null;
    }
    
}
