package com.siliver.ch1.test.mock;

import com.siliver.ch1.service.CreditSystemService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.lt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreditServiceMockTest {
    @Test
    public void test(){
        int userId=10;
        //创建mock对象
        CreditSystemService creditSystemService=mock(CreditSystemService.class);
        //模拟mock对象调用
        when(creditSystemService.getUserCredit(eq(userId))).thenReturn(1000);
        //进行实际调用
        int ret=creditSystemService.getUserCredit(userId);
        //
        creditSystemService.getUserCredit(userId);
        //进行期望值和返回值的比较
        assertEquals(1000,ret);
        verify(creditSystemService,times(2)).getUserCredit(eq(userId));
    }

    @Test
    public void test2(){
        int userId=10;
        //创建mock对象
        CreditSystemService creditSystemService=mock(CreditSystemService.class);
        //模拟mock对象调用
        when(creditSystemService.getUserCredit(eq(userId))).thenReturn(1000);
        when(creditSystemService.getUserCredit(lt(0))).thenThrow(new IllegalArgumentException("userId不能小于0"));

        try{
            //实际调用
            int ret=creditSystemService.getUserCredit((10));
            Assert.assertEquals(1000,ret);
            ret=creditSystemService.getUserCredit(-1);
            Assert.fail();
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void Test3(){
        List list=mock(List.class);
        doThrow(new UnsupportedOperationException("不支持clear犯法调用")).when(list).clear();
        try{
            list.clear();
        }catch (UnsupportedOperationException x){
            return;
        }
        Assert.fail();
    }
}
