CREATE TABLE t_a_capteur_unitemesurer_acu (
    uni_id INTEGER,
    cap_id INTEGER,
    acu_plagemin INTEGER,
    acu_plagemax INTEGER,
    acu_precision INTEGER,
    PRIMARY KEY (uni_id, cap_id),
    FOREIGN KEY (uni_id) REFERENCES t_e_unitemesurer_uni(uni_id),
    FOREIGN KEY (cap_id) REFERENCES t_e_capteur_cap(cap_id)
);
