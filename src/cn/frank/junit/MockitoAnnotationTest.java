/**
 * (C) Copyright Frank Fan -  All Rights Reserved.
 */
package cn.frank.junit;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * mock object by annotation
 * @author: jun.fan2@hp.com
 * @version: 1.0
 * @since: 2012-12-12
 * @changeLog:
 * 
 */
public class MockitoAnnotationTest
{

  @Mock 
  private List<String> testList ;
  
  @Before
  public void setUp() throws Exception
  {
    initMocks(this);
  }
  
  @Test
  public void testAnnotationMock() throws Exception
  {
    testList.add(anyString());
    verify(testList).add(anyString());
  }

}
