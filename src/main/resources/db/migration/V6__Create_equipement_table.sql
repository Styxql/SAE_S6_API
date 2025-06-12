CREATE TABLE t_e_equipement_equ (
    equ_id SERIAL PRIMARY KEY,
    equ_nom VARCHAR(255) NOT NULL,
    equ_description TEXT,
    equ_dimensions VARCHAR(255),
    equ_image_url VARCHAR(255),
    equ_marque_id INTEGER,
    equ_type_equipement_id INTEGER,
    FOREIGN KEY (marque_id) REFERENCES t_e_marque_maq(id),
    FOREIGN KEY (type_equipement_id) REFERENCES t_e_typeequipement_tye(id)
);
