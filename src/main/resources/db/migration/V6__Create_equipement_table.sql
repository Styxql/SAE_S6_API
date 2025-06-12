CREATE TABLE t_e_equipement_equ (
    equ_id SERIAL PRIMARY KEY,
    equ_nom VARCHAR(255) NOT NULL,
    equ_description TEXT,
    equ_dimensions VARCHAR(255),
    equ_image_url VARCHAR(255),
    mar_id INTEGER,
    teq_id INTEGER,
    FOREIGN KEY (mar_id) REFERENCES t_e_marque_mar(mar_id),
    FOREIGN KEY (teq_id) REFERENCES t_e_typeequipement_teq(teq_id)
);
