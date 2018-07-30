package com.pawn.javalib;

/*********************************************
 * @文件名称: Test
 * @文件作者: Pawn
 * @文件描述:
 * @创建时间: 2018/7/26 13
 * @修改历史:
 *********************************************/
public class Test {

    public static void main(String[] args) {
        String s = "12.9";
        System.out.println((int) Double.parseDouble(s));

        int[] arr = {1, 90, 3, 4, 78, 4, 9};
        bubbleSort(arr);
        printf(arr);
    }

    public static void bubbleSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    arr[j] = arr[j] ^ arr[j + 1];
                    arr[j + 1] = arr[j] ^ arr[j + 1];
                    arr[j] = arr[j] ^ arr[j + 1];
                }
            }
        }
    }

    public static void printf(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
