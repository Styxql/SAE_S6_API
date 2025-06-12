CREATE TABLE t_e_equipement_equ (
    equ_id SERIAL PRIMARY KEY,
    equ_nom VARCHAR(255) NOT NULL,
    equ_description TEXT,
    equ_hauteur NUMERIC(5,2),
    equ_longueur NUMERIC(5,2),
    equ_largeur NUMERIC(5,2),
    equ_urlimg VARCHAR(1024),
    mar_id INTEGER,
    teq_id INTEGER,
    FOREIGN KEY (mar_id) REFERENCES t_e_marque_mar(mar_id),
    FOREIGN KEY (teq_id) REFERENCES t_e_typeequipement_teq(teq_id)
);
