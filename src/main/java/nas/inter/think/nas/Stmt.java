package nas.inter.think.nas;

public abstract class Stmt {

    interface Visitor<R>{

    }
    abstract <R> R accept(Visitor<R> visitor);
}
