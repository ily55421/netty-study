package com.link.example.handler;

/**
 * @Author: linK
 * @Date: 2022/7/19 14:33
 * @Description TODO
 */
public class NumTest {
    public static void main(String[] args) {
        char[][] a = new char[2][3];
//        strcpy(a[0], "ab");
//        strcpy(a[1], "cd");
//        printf("%s", a);
//        a数组里面的内容为ab\0cd\0，printf输出\0前面的内容，所以结果是ab。
//        除了这个题，我还做过另一道题，在这段代码基础上，在第三四行之间加一句a[1][2]=" ";
//        此时a里面内容为ab cd\0，所以printf输出内容为ab cd。
    }
}

class Solution {
//        public ListNode ReverseList(ListNode head) {
//
//            if(head==null)
//                return null;
//            //head为当前节点，如果当前节点为空的话，那就什么也不做，直接返回null；
//            ListNode pre = null;
//            ListNode next = null;
//            //当前节点是head，pre为当前节点的前一节点，next为当前节点的下一节点
//            //需要pre和next的目的是让当前节点从pre->head->next1->next2变成pre<-head next1->next2
//            //即pre让节点可以反转所指方向，但反转之后如果不用next节点保存next1节点的话，此单链表就此断开了
//            //所以需要用到pre和next两个节点
//            //1->2->3->4->5
//            //1<-2<-3 4->5
//            while(head!=null){
//                //做循环，如果当前节点不为空的话，始终执行此循环，此循环的目的就是让当前节点从指向next到指向pre
//                //如此就可以做到反转链表的效果
//                //先用next保存head的下一个节点的信息，保证单链表不会因为失去head节点的原next节点而就此断裂    next = 2  null  1 2   1 2 null   next 3 1 2   2 3->1
//                next = head.next;
//                //保存完next，就可以让head从指向next变成指向pre了，代码如下
//                head.next = pre;
//                //head指向pre后，就继续依次反转下一个节点
//                //让pre，head，next依次向后移动一个节点，继续下一次的指针反转   2->1  3->2
//                pre = head;   1->null 2->1 3->2 4->3 5->4
//                head = next;  2->3 3->4 4->5
//            }
//            //如果head为null的时候，pre就为最后一个节点了，但是链表已经反转完毕，pre就是反转后链表的第一个节点
//            //直接输出pre就是我们想要得到的反转后的链表
//            return pre;

//            每次循环的情况写出来，假设初始链表是 0 -> 1 -> 2 -> 3 -> 4
//// 0
//-> 1 -> 2 -> 3 -> 4   oldHead指向0，
//            newHead指向0，toBeReversed指向1
//
//// 1 -> 0 -> 2 -> 3 -> 4   oldHead指向0， newHead指向1，toBeReversed指向2
//
//// 2 -> 1 -> 0 -> 3 -> 4  oldHead指向0，
//            newHead指向2，toBeReversed指向3
//// 3 -> 2 -> 1 -> 0 -> 4
//            oldHead指向0， newHead指向3，toBeReversed指向4
//// 4 -> 3 -> 2 ->
//            1 -> 0   oldHead指向0， newHead指向4，toBeReversed指向null
//            public
//            ListNode ReverseList(ListNode head) {
//                if (head == null ||
//                        head.next == null) {
//                    return head;
//                }
//
//                ListNode oldHead = head;
//                ListNode newHead =
//                        head;
//                ListNode toBeReversed = head.next;
//                do
//                {
//                    oldHead.next = toBeReversed.next;
//
//                    toBeReversed.next = newHead;
//                    newHead =
//                            toBeReversed;
//                    toBeReversed = oldHead.next;
//
//                } while (toBeReversed!=null);
//                return newHead;
//            }
}