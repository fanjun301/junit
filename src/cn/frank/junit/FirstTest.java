package cn.frank.junit;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class FirstTest
{

	
  @Before
  public void setUp(){
    System.out.println("This is test initial");
  }
  
  @Test(timeout=3000)
  public void testFirst() throws Exception
  {
    assertThat(0,not(1));
    assertThat("挂了，故意挂的", 0, is(1));
  }
  
  @Test(expected = NullPointerException.class)
  public void testException() throws Exception
  {
    throw new NullPointerException();
  }
  
}
