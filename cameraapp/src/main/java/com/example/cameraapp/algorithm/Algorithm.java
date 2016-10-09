package com.example.cameraapp.algorithm;

import com.example.cameraapp.logutlis.LogUtlis;

import java.util.Date;

/**
 * Created by user on 2016/6/22.
 */
public class Algorithm
{


    /**
     * 直插算法
     */
    public static void insertSort(){
        int[] a ={49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,
                34,15,35,25,53,51,49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34
                ,15,35,25,53,51};
        int temp = 0;
        LogUtlis.e("直插算法","开始："+new Date().getTime()+"");
        for (int i = 1;i<a.length;i++){
            int j = i-1;
            temp = a[i];
            for (;j>=0&&temp<a[j];j--){
                a[j+1] = a[j];
            }
            a[j+1] = temp;
        }
        LogUtlis.e("直插算法","开始："+new Date().getTime()+"");
//        for (int i = 0;i<a.length;i++){
//            LogUtlis.e("直插算法",a[i]+"");
//        }

    }

    public void quickSort (){
        int a[]={49,38,65,97,76,13,64,5,4,1};
        LogUtlis.e("快速算法","开始："+new Date().getTime()+"");
        quick(a);
        LogUtlis.e("快速算法","结束："+new Date().getTime()+"");
//        for(int i=0;i<a.length;i++){
//            LogUtlis.e("快速算法",a[i]+"");
//        }

    }

    public int getMiddle(int[] list, int low, int high)
    {
        int tmp =list[low];    //数组的第一个作为中轴
        while (low < high){
            while (low < high && list[high] >= tmp) {
                high--;
            }
            list[low] =list[high];   //比中轴小的记录移到低端
            while (low < high&& list[low] <= tmp) {
                low++;
            }
            list[high] =list[low];   //比中轴大的记录移到高端

        }
        list[low] = tmp;              //中轴记录到尾
        return low;                   //返回中轴的位置
    }
    public void _quickSort(int[] list, int low, int high) {
        if (low < high){
            int middle =getMiddle(list, low, high);  //将list 数组进行一分
            _quickSort(list, low, middle - 1);       //对低字表进行递归排
            _quickSort(list,middle + 1, high);      //对高字表进行递归排
        }
    }

    public void quick (int[] a2){
        if(a2.length>0){
            _quickSort(a2,0, a2.length - 1);
        }
    }
}
