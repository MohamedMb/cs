select 
    *
from
    facebook.utilisateur u
        inner join
    facebook.ami a ON u.ID = a.ID
where
    exists( select 
            *
        from
            facebook.ami a2
        where
            a2.ID_1 = a.ID and a2.ID = a.ID_1)