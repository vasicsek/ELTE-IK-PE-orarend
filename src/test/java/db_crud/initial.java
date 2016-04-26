
package db_crud;

import com.elte.osz.logic.OszDS;
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
    public void selection(){
        
    }

    
}
