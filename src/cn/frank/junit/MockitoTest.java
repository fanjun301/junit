package cn.frank.junit;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoTest
{

  private List<String> testList = null;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception
  {
    testList = mock(ArrayList.class);
  }

  @Test
  public void testVerifyMethod() throws Exception
  {
    String param = anyString();
    testList.add(param);
    verify(testList).add(param);
  }

  @Test
  public void testStubbing() throws Exception
  {
    when(testList.get(anyInt())).thenReturn("");
    
    assertThat("Mock Object get first element ", testList.get(0), anything());
    assertThat("Mock Object get none mock call ", testList.remove(anyInt()), nullValue());
  }

  @Test
  public void testArgument() throws Exception
  {
    when(testList.get(anyInt())).thenReturn("");
    testList.get(anyInt());
    testList.get(anyInt());

    verify(testList, times(2)).get(anyInt());
    verify(testList, atLeastOnce()).get(anyInt());
    verify(testList, atMost(2)).get(anyInt());
    verify(testList, atLeast(2)).get(anyInt());
  }
  
  @Test(expected = Exception.class)
  public void testStubWithException() throws Exception
  {
    doThrow(new Exception("故意找个茬")).when(testList).clear();
    testList.clear();
    
    when(testList.add(anyString())).thenThrow(new Exception("再找个茬"));
    testList.add("test");
  }
  
  @Test
  public void testInOrder() throws Exception
  {
    testList.add("2");
    testList.add("1");
    
    InOrder inOrder = inOrder(testList);
    inOrder.verify(testList).add("2");
    inOrder.verify(testList).add("1");
  }
  
  @Test
  public void testNeverHappened() throws Exception
  {
    @SuppressWarnings("unchecked")
    List<String> temp = mock(ArrayList.class);
    
    testList.add("test");
    verify(testList).add(anyString());
    verify(testList,never()).get(anyInt());
    verifyZeroInteractions(temp);
  }
  
  @Test
  public void testRedundantInvocation() throws Exception
  {
    testList.add("test1");
    testList.add("test2");
    verify(testList,times(2)).add(anyString());
    verifyNoMoreInteractions(testList);
  }
  
  @Test
  public void testConsecutiveCalls() throws Exception
  {
    when(testList.add(anyString()))
    .thenReturn(true)
    .thenReturn(false);
    
    when(testList.get(anyInt()))
    .thenReturn("test1", "test2");
    
    assertThat(testList.add("test1"),is(true));
    assertThat(testList.add("test2"),is(false));
    assertThat(testList.get(0), is("test1"));
    assertThat(testList.get(1), is("test2"));
    
  }

  @Test
  public void testCallback() throws Exception
  {
    when(testList.add(anyString())).then(new Answer<Boolean>(){

      @Override
      public Boolean answer(InvocationOnMock invocation) throws Throwable
      {
        Object[] args = invocation.getArguments();
        for (Object object : args)
        {
          Pattern compile = Pattern.compile("\\d");
          if (compile.matcher(object.toString()).find())
          {
            return false;
          }
        }
        return true;
      }
      
    });
    
    assertThat(testList.add("test"),is(true));
    assertThat(testList.add("21831135"),is(false));
  }
  
  
  @Test(expected=RuntimeException.class)
  public void testDoFamily() throws Exception
  {
    doReturn(false).when(testList).add(anyString());
    //---------------------------------------------
    doAnswer(new Answer<Boolean>(){

      @Override
      public Boolean answer(InvocationOnMock invocation) throws Throwable
      {
        return false;
      }

    }).when(testList).set(anyInt(), anyString());
    //-----------------------------------------------
    doNothing().when(testList).clear(); //only void method can call doNothing
    
    doThrow(new RuntimeException()).when(testList).remove(anyInt()); 
    
    testList.add(anyString());
    testList.set(anyInt(), anyString());
    testList.clear();
    testList.remove(anyInt());
  }
  
  @Ignore
  @Test
  public void testSpy() throws Exception
  {
    //TODO
  }
  
  @Test
  public void testCapture() throws Exception
  {
    String param = "test";
    ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
    testList.add(param);
    verify(testList).add(argument.capture());
    assertThat(param, equalTo(argument.getValue()));
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testResetMock() throws Exception
  {
    testList.add("test");
    verify(testList,atLeastOnce()).add(anyString());
    reset(testList);
    verify(testList,never()).add(anyString());
  }

}
