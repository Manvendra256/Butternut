package com.example.butternut.util

import android.util.Log

class FenwickTree(flashCards: Array<Pair<Long, Int>>) {

    private val n: Int
    private val bit: IntArray
    private var sum: Int = 0

    init {
//        println("Initializing bit")
        n = flashCards.size+1
        bit = IntArray(n)
        for(i in flashCards.indices)
            add(i, 6-flashCards[i].second)
    }

    fun query(J: Int): Int{
        var ans: Int = 0;
        var j = J+1;
        while(j>0){
            ans+=bit[j]
            j-= (j and -j)
        }
        return ans
    }

    fun query(i: Int, j: Int): Int{
        return query(j)-query(i-1)
    }

    fun print(){
        var temp: String = "Fenwick Tree "
        for (i in 0..n-2)
            temp+=query(i,i).toString() + " "
        println(temp)
    }

    fun add(index: Int, value: Int){
//        println("Adding $value to $index")
        var i: Int = index+1
        sum+=value
//        Log.d("New sum" ,sum.toString())
        while(i<n){
            bit[i]+=value
            i+= (i and -i)
        }
    }

    fun find_random() : Int{
        var pos: Int = 0
        if(sum==0) return -1
        var sum: Int = (1..sum).random();
        var i = 31-n.countLeadingZeroBits();
        while(i>=0){
            if((pos+(1 shl i)<n) && (bit[pos+(1 shl i)]<sum)){
                pos += (1 shl i)
                sum-=bit[pos]
            }
            i--;
        }
        return pos
    }


}

