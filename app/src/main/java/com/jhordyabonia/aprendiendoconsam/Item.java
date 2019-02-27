package com.jhordyabonia.aprendiendoconsam;


public class Item {
    private static int[] AUDIOS={
            R.raw.cero,R.raw.uno,R.raw.dos,R.raw.tres,R.raw.cuatro,
            R.raw.cinco,R.raw.seis,R.raw.siete,R.raw.ocho,R.raw.nueve,
            R.raw.a,R.raw.b,R.raw.c,R.raw.d,R.raw.e,R.raw.f,R.raw.g,R.raw.h,
            R.raw.i,R.raw.j,R.raw.k,R.raw.l,R.raw.m,R.raw.n,R.raw.n_,R.raw.o,
            R.raw.p,R.raw.q,R.raw.r,R.raw.s,R.raw.t,R.raw.u,R.raw.v,R.raw.w,
            R.raw.x,R.raw.y,R.raw.z
    };
    private static int[] IMAGES= {
            R.drawable.cero1,R.drawable.uno1,R.drawable.dos1,R.drawable.tres1,R.drawable.cuatro1,
            R.drawable.cinco1,R.drawable.seis1,R.drawable.siete1,R.drawable.ocho1,R.drawable.nueve1,
            R.drawable.a1,R.drawable.b1,R.drawable.c1,R.drawable.d1,R.drawable.e1,R.drawable.f1,R.drawable.g1,R.drawable.h1,
            R.drawable.i1,R.drawable.j1,R.drawable.k1,R.drawable.l1,R.drawable.m1,R.drawable.n1,R.drawable.n_1,R.drawable.o1,
            R.drawable.p1,R.drawable.q1,R.drawable.r1,R.drawable.s1,R.drawable.t1,R.drawable.u1,R.drawable.v1,R.drawable.w1,
            R.drawable.x1,R.drawable.y1,R.drawable.z1
    };

    public  enum LETTERS {cero,uno,dos,tres,cuatro,cinco,seis,siete,ocho,nueve,a,b,c,d,e,f,g,h,i,j,k,l,m,n,n_,o,p,q,r,s,t,u,v,w,x,y,z}
    public final String item;
    public int image;
    public int audio;
    public Item(LETTERS i){
        item=i.name();
        audio=AUDIOS[i.ordinal()];
        image=IMAGES[i.ordinal()];
    }
}
