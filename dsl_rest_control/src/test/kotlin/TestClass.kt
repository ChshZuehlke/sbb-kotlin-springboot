import org.junit.Test

class TestClass{


    @Test
    fun test(){
        val base1Impl = Base1Impl()

        val base2Impl = Base2Impl()

        val derived = Derived(base1Impl,base2Impl)
    }


}


class Base1Impl: Base1{
    override fun getName1(): String = "Base1"

}

class Base2Impl: Base2{
    override fun getName2(): String = "Base2"

}


class Derived(base1: Base1,base2: Base2): Base1 by base1, Base2 by base2

interface Base1{

    fun getName1(): String
}

interface Base2{

    fun getName2():String
}