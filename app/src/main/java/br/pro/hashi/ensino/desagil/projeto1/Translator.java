package br.pro.hashi.ensino.desagil.projeto1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


// Não é permitido mudar nada nessa classe
// exceto o recheio dos três métodos.

public class Translator {
    private final Node root;
    private final HashMap<Character, Node> map;


    // Você deve mudar o recheio deste construtor,
    // de acordo com os requisitos não-funcionais.
    public Translator() {
        map = new HashMap<Character, Node>();
        root = new Node(' ');

        Node T = new Node('T');
        root.setLeft(T);
        T.setParent(root);

        Node M = new Node('M');
        T.setLeft(M);
        M.setParent(T);

        Node O = new Node('O');
        M.setLeft(O);
        O.setParent(M);

        Node BUF = new Node('-');
        O.setLeft(BUF);
        BUF.setParent(O);

        Node ZERO = new Node('0');
        BUF.setLeft(ZERO);
        ZERO.setParent(BUF);

        Node NOVE = new Node('9');
        BUF.setRight(NOVE);
        NOVE.setParent(BUF);

        Node BUF2 = new Node('.');
        O.setRight(BUF2);
        BUF2.setParent(O);

        Node OITO = new Node('8');
        BUF2.setRight(OITO);
        OITO.setParent(BUF2);

        Node G = new Node('G');
        M.setRight(G);
        G.setParent(M);

        Node Z = new Node('Z');
        G.setRight(Z);
        Z.setParent(G);

        Node SETE = new Node('7');
        Z.setRight(SETE);
        SETE.setParent(Z);

        Node N = new Node('N');
        T.setRight(N);
        N.setParent(T);

        Node D = new Node('D');
        N.setRight(D);
        D.setParent(N);

        Node B = new Node('B');
        D.setRight(B);
        B.setParent(D);

        Node SEIS = new Node('6');
        B.setRight(SEIS);
        SEIS.setParent(B);

        Node K = new Node('K');
        N.setLeft(K);
        K.setParent(N);

        Node Y = new Node('Y');
        K.setLeft(Y);
        Y.setParent(K);

        Node C = new Node('C');
        K.setRight(C);
        C.setParent(K);

        Node X = new Node('X');
        D.setLeft(X);
        X.setParent(D);

//=============================================

        Node E = new Node('E');
        root.setRight(E);
        E.setParent(root);

        Node A = new Node('A');
        E.setLeft(A);
        A.setParent(E);

        Node W = new Node('W');
        A.setLeft(W);
        W.setParent(A);

        Node J = new Node('J');
        W.setLeft(J);
        J.setParent(W);

        Node UM = new Node('1');
        J.setLeft(UM);
        UM.setParent(J);

        Node P = new Node('P');
        W.setRight(P);
        P.setParent(W);

        Node R = new Node('R');
        A.setRight(R);
        R.setParent(A);

        Node L = new Node('L');
        R.setRight(L);
        L.setParent(R);

        Node I = new Node('I');
        E.setRight(I);
        I.setParent(E);

        Node S = new Node('S');
        I.setRight(S);
        S.setParent(I);

        Node H = new Node('H');
        S.setRight(H);
        H.setParent(S);

        Node CINCO = new Node('5');
        H.setRight(CINCO);
        CINCO.setParent(H);

        Node QUATRO = new Node('4');
        H.setLeft(QUATRO);
        QUATRO.setParent(H);

        Node V = new Node('V');
        S.setLeft(V);
        V.setParent(S);

        Node TRES = new Node('3');
        V.setLeft(TRES);
        TRES.setParent(V);


        Node U = new Node('U');
        I.setLeft(U);
        U.setParent(I);

        Node BUF3 = new Node('-');
        U.setLeft(BUF3);
        BUF3.setParent(U);

        Node F = new Node('F');
        U.setRight(F);
        F.setParent(U);

        Node DOIS = new Node('2');
        BUF3.setLeft(DOIS);
        DOIS.setParent(BUF3);

        Node Q = new Node('Q');
        G.setLeft(Q);
        Q.setParent(G);

        map.put('A', A);
        map.put('B', B);
        map.put('C', C);
        map.put('D', D);
        map.put('E', E);
        map.put('F', F);
        map.put('G', G);
        map.put('H', H);
        map.put('I', I);
        map.put('J', J);
        map.put('K', K);
        map.put('L', L);
        map.put('M', M);
        map.put('N', N);
        map.put('O', O);
        map.put('P', P);
        map.put('Q', Q);
        map.put('R', R);
        map.put('S', S);
        map.put('T', T);
        map.put('U', U);
        map.put('V', V);
        map.put('W', W);
        map.put('X', X);
        map.put('Y', Y);
        map.put('Z', Z);
        map.put('1', UM);
        map.put('2', DOIS);
        map.put('3', TRES);
        map.put('4', QUATRO);
        map.put('5', CINCO);
        map.put('6', SEIS);
        map.put('7', SETE);
        map.put('8', OITO);
        map.put('9', NOVE);
        map.put('0', ZERO);
    }


    // Você deve mudar o recheio deste método, de
    // acordo com os requisitos não-funcionais.
    public char morseToChar(String code) {
        Node inicio = root;

        for (int i = 0; i < code.length(); i++) {

            char ponto = code.charAt(i);

            if (ponto == '.') {

                inicio = inicio.getRight();

            } else if (ponto == '-') {

                inicio = inicio.getLeft();
            }

        }
        if (inicio != null) {

            return Character.toLowerCase(inicio.getValue());

        } else {

            return ' ';
        }
    }


    // Você deve mudar o recheio deste método, de
    // acordo com os requisitos não-funcionais.
    public String charToMorse(char c) {
        StringBuilder codigo = new StringBuilder();

        Node inicio = map.get(Character.toUpperCase(c));

        while (inicio != root) {
            if (inicio.getParent().getRight() == inicio) {

                codigo.insert(0, '.');

            } else if (inicio.getParent().getLeft() == inicio) {

                codigo.insert(0, '-');
            }
            inicio = inicio.getParent();
        }
        return codigo.toString().toLowerCase();
    }


    // Você deve mudar o recheio deste método, de
    // acordo com os requisitos não-funcionais.
    public LinkedList<String> getCodes() {
        Queue<Node> queue = new LinkedList<>();

        LinkedList<String> lista = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.element();
            Node left = node.getLeft();
            Node right = node.getRight();
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }
            if (node.getValue() != ' ' && node.getValue() != '-' && node.getValue() != '.') {
                lista.add(charToMorse(node.getValue()));

            }
            queue.remove();

        }
        return lista;

    }
}