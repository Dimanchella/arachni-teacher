package ru.mai.arachni.teacher.xmlloader;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@Data
@NoArgsConstructor
@XmlRootElement(name = "doc")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"title", "categories", "creator", "creationDate", "text"})
public class Article {
    @XmlElement(name = "title")
    private ArticleField title = new ArticleField();
    @XmlElement(name = "categories")
    private ArticleField categories = new ArticleField();
    @XmlElement(name = "creator")
    private ArticleField creator = new ArticleField();
    @XmlElement(name = "creation_date")
    private ArticleField creationDate = new ArticleField();
    @XmlElement(name = "text")
    private ArticleField text = new ArticleField();

    public Article(String title, String category, String creator, String creationDate, String text) {
        this.title.setText(title);
        this.categories.setText(category);
        this.creator.setText(creator);
        this.creationDate.setText(creationDate);
        this.text.setText(text);
    }

    /**
     * Вспомогательный класс добавляющий атрибуты полям класса Article
     */
    @Data
    public static class ArticleField {
        @XmlValue
        @XmlCDATA
        public String text;
        @XmlAttribute
        public boolean auto;
        @XmlAttribute
        public String type;
        @XmlAttribute
        public boolean verify;
    }
}
