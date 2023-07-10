package com.example.proyecto_moviles.domain;

import java.io.Serializable;

public class Categoria implements Serializable {

    private String categoria;
    private String subCategoria;

    public Categoria() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }


    /*
    "categorias":[
            {
                "categoria":"Programacion",
                    "subCategoria":"Java"
            }
    ]
}
*/
}
