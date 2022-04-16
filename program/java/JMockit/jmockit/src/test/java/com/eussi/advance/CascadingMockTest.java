package com.eussi.advance;

import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.Test;

//级联Mock:对Mock对象的方法返回再进行Mock
public class CascadingMockTest {

    @Test
    public void testCascading() {
        //下面以Mock  EntityManager.createNativeQuery的返回对象为例
        ReturnInterface returnInterface = new MockUp<ReturnInterface>() {
            @Mock
            public ReturnInnerInterface getReturnInnerInterface() {
                return new ReturnInnerInterface() {
                    @Override
                    public int getNumber() {
                        return 33;
                    }

                    @Override
                    public String getString(String str) {
                        return null;
                    }
                };
            }
        }.getMockInstance();
        //可以发现，我们成功地对entityManager.createNativeQuery方法返回值进行了Mock
        Assert.assertTrue(returnInterface.getReturnInnerInterface().getNumber() == 33);
    }
}
