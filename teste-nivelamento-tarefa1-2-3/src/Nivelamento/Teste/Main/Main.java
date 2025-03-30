package Nivelamento.Teste.Main;

import Nivelamento.Teste.Teste1.WebScraping;
import Nivelamento.Teste.Teste2.TransformacaoDeDados;
import Nivelamento.Teste.Teste3.TarefasDePreparacao1;
import Nivelamento.Teste.Teste3.TarefasDePreparacao2;

public class Main {
    public static void main(String[] args) {
        WebScraping a = new WebScraping();
        TransformacaoDeDados b = new TransformacaoDeDados();
        TarefasDePreparacao1 c = new TarefasDePreparacao1();
        TarefasDePreparacao2 d = new TarefasDePreparacao2();

        a.main();
        b.main();
        c.main();
        d.main();
    }
}