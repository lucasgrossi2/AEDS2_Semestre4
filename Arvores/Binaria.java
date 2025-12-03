package Arvores;

class No {
    int elemento;
    No esq;
    No dir;

    public No (int elemento, No esq, No dir){
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }

    public No (int elemento){
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }

}

class Arvore {
    No raiz;
    public Arvore(){
        this.raiz = null;
    }

    boolean pesquisar(int alvo , No i){
        boolean resp = false;
        if(i == null){
            resp = false;
        } else if (i.elemento == alvo){
            resp = true;
        } else if(alvo > i.elemento){
            pesquisar(alvo, i.dir);
        } else if(alvo < i.elemento){
            pesquisar(alvo, i.esq);
        }
        return resp;
    }

    No inserir(int x, No i){
        if(i == null){
            i = new No(x);
        } else if(x > i.elemento){
            inserir(x, i.dir);
        } else if(x < i.elemento){
            inserir(x, i.esq);
        }
        return i;
    }
}

public class Binaria {
    public static void main(String[] args){

    }
}
