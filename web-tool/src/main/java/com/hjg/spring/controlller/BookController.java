package com.hjg.spring.controlller;

import com.hjg.spring.model.Book;
import com.hjg.spring.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/13
 */
@RestController
@RequestMapping(value = "/cka")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm:ss");

    @Autowired
    private Book book;

    @ExceptionHandler({NullPointerException.class})
    public String exHandler(NullPointerException ex, HttpServletRequest request) {
        String bookName = request.getParameter("name");
        logger.info("bookName = {}", bookName);
        return "null exception";
    }

    @RequestMapping("/return/ex/book")
    public Book exBook(String name) {
        //http://localhost:8081/cka/return/ex/book?name=Java
        Book book = new Book();
        book = null;
        book.setName(name);
        return book;
    }

    @ModelAttribute
    public void addModelBook(Model model) {
        Book book = new Book();
        book.setName("VB");
        book.setAuthor("MicroSoft");
        model.addAttribute("book", book);
    }

    @ModelAttribute(name = "default_book")
    public Book defaultBook() {
        Book book = new Book();
        book.setName("C#");
        book.setAuthor("Microsoft");
        //如果不在@ModelAttribute上写属性值，默认属性名称会是book。
        return book;
    }

    /*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //进行数据绑定时，匹配目标对象的Date字段的名称的参数的值，会从String转换为Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));

        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }
    */

    @RequestMapping("/return/date/book")
    public Book dateBook(Book book) {
        //先从model获取一个book对象，即addModelBook方法配置的book对象
        //然后会进行数据绑定，这时会将String转换为Date，赋予给publishDate字段。
        //访问http://localhost:8081/cka/return/date/book?publishDate=2020-12-12&price=22,333.54
        //会发现确实如上所示。由于Book的publishDate字段加上了@DateTimeFormat，不需要再手动注册属性编辑器。
        logger.info("book = {}", book);
        return book;
    }

    @RequestMapping("/return/book")
    public String returnBook() {
        //这个时间每次都不一样，足以证明每次获取的对象不一样。
        Date publishDate = book.getPublishDate();
        Instant instant = publishDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        String str = df.format(instant.atZone(zoneId).toLocalDateTime());
        return str;
    }

    @RequestMapping("/return/{jim}/person")
    public Person returnPerson(@ModelAttribute(value = "jim", binding = false) Person person, Model model) {
        logger.info("person = {}", person);
        //这里的person对象来自Model，先进行类型转换再将其放在模型中。
        //通过http://localhost:8081/cka/return/jim_20/person?weight=60访问时，默认使用了数据绑定
        //这时会注入weight字段，如果设置binding=false，weight字段是默认值
        Person person2 = (Person) model.getAttribute("jim");
        return person2;
    }

    @RequestMapping("/return/converter/person")
    public Person convertPerson(@RequestParam("person") Person person, @RequestParam("birthDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthDay) {
        //http://localhost:8081/cka/return/converter/person?person=Jack_22&birthDay=2020-11-11
        //通过自定义的类型转换器而不是数据绑定，获得Person(Jack和22)。所以是基于String的参数转换为对象，而不是多个参数绑定到对象。
        //通过默认Date格式化器获得birthDay。
        //@DateTimeFormat可以放在对象的字段上，数据绑定时进行转换。
        logger.info("original person = {}, birthDay = {}", person, birthDay);
        person.setBirthDay(birthDay);
        return person;
    }

    @RequestMapping("/return/default/book")
    public Book getDefaultBook(@ModelAttribute(name = "default_book") Book book) {
        //这里如果不写@ModelAttribute，根据类型是需要属性名为book的对象。
        return book;
    }

    @RequestMapping("/return/model/book")
    public Book getModelBook(Book book) {
        return book;
    }
}
