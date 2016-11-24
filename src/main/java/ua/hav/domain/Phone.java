package ua.hav.domain;

import ua.hav.domain.annotations.Display;
import ua.hav.domain.annotations.Id;
import ua.hav.domain.annotations.Reference;
import ua.hav.domain.annotations.Table;

import javax.persistence.GeneratedValue;

/**
 * Created by Юля on 24.08.2016.
 */
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue
    @Display(size = 3)
    private int id;

    @Reference
    @Display(size = 10)
    private User user;

    @Display(size = 10)
    private String phone;


}
