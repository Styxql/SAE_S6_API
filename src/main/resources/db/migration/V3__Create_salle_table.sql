CREATE TABLE t_e_salle_sal (
    sal_id SERIAL PRIMARY KEY,
    sal_nom VARCHAR(255) NOT NULL,
    sal_urlimg VARCHAR(1024), 
    sal_capacite INTEGER,
    bat_id INTEGER NOT NULL,
    typ_id INTEGER,
    FOREIGN KEY (bat_id) REFERENCES t_e_batiment_bat(bat_id),
    FOREIGN KEY (typ_id) REFERENCES t_e_typesalle_typ(typ_id)
);
