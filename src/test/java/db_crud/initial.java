
package db_crud;

import com.elte.osz.logic.Building;
import com.elte.osz.logic.OszDS;
import com.elte.osz.logic.controllers.RoomJpaController;
import com.elte.osz.logic.entities.Room;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class initial {
    
    private OszDS ds;
    
    public initial() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ds = new OszDS();
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void test1(){
        Building b = Building.AjtosiDurerSor;
        
        RoomJpaController ctrl = ds.getCtrlRoom();
        
        Room room = new Room();
        room.setBuilding(Building.DeliTomb);
        room.setFloor((byte) 0);
        room.setName("0-821 Bolyai János");
        ctrl.create(room);
    }

    
}
