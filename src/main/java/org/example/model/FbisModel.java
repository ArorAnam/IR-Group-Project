package org.example.model;

public class FbisModel {
    String docno, text, h2, h3, date1, ht, ti; // Important to parse
    // Header contains majorly all the tags
    // Text also contains majorly all the tags
    String txt5, fig, header, f;

    String h1, h4, h5, h6, h7, h8;

    public String getHt() {
        return ht;
    }

    public void setHt(String ht) {
        this.ht = ht;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getH2() {
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public String getH3() {
        return h3;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }

    public String getH4() {
        return h4;
    }

    public void setH4(String h4) {
        this.h4 = h4;
    }

    public String getH5() {
        return h5;
    }

    public void setH5(String h5) {
        this.h5 = h5;
    }

    public String getH6() {
        return h6;
    }

    public void setH6(String h6) {
        this.h6 = h6;
    }

    public String getH7() {
        return h7;
    }

    public void setH7(String h7) {
        this.h7 = h7;
    }

    public String getH8() {
        return h8;
    }

    public void setH8(String h8) {
        this.h8 = h8;
    }
    public String getDocno() {
        return this.docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTxt5() {
        return this.txt5;
    }

    public void setTxt5(String txt5) {
        this.txt5 = txt5;
    }

    public String getDate1() {
        return this.date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getFig() {
        return this.fig;
    }

    public void setFig(String fig) {
        this.fig = fig;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getF() {
        return this.f;
    }

    public void setF(String f) {
        this.f = f;
    }

}