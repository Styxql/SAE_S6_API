CREATE TABLE t_e_equipementinstalle_ein (
    ein_id SERIAL PRIMARY KEY,
    ein_nombre INT NOT NULL,
    equ_id INTEGER,
    sal_id INTEGER,
    FOREIGN KEY (equ_id) REFERENCES t_e_equipement_equ(equ_id),
    FOREIGN KEY (sal_id) REFERENCES t_e_salle_sal(sal_id)
);
