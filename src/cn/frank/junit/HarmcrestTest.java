package cn.frank.junit;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HarmcrestTest
{

  @SuppressWarnings("unchecked")
  @Test
  public void testAllOf() throws Exception
  {
    assertThat("Harmcrest AllOf ", true, allOf(is(true), not(false))); 
    assertThat("Harmcrest AllOf ", true, not(allOf(is(true), is(false)))); 
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testAnyOf() throws Exception
  {
    assertThat("Harmcrest AnyOf ", true, anyOf(is(true),is(false)));
  }


  @Test
  public void testIsOrIsNot() throws Exception
  {
    assertThat("Harmcrest Is ", true, is(true));
    assertThat("Harmcrest IsNot ", true, not(false));
  }
  
  @Test
  public void testIsEqual() throws Exception
  {
    assertThat("Harmcrest IsEqual ",21, equalTo(3*7));
  }
  
  @Test
  public void testIsNull() throws Exception
  {
    assertThat("Harmcrest IsNull ", null, nullValue());
    assertThat("Harmcrest IsNotNull ", true, notNullValue());
  }
   
}
