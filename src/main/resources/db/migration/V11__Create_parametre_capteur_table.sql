CREATE TABLE t_a_capteur_unitemesurer_acu (
    acu_id_unite_mesurer INTEGER,
    acu_id_capteur INTEGER,
    acu_plage_mesure VARCHAR(255),
    acu_precision VARCHAR(255),
    PRIMARY KEY (id_unite_mesurer, id_capteur),
    FOREIGN KEY (id_unite_mesurer) REFERENCES t_e_unitemesurer_ume(id),
    FOREIGN KEY (id_capteur) REFERENCES t_e_capteur_cap(id)
);
