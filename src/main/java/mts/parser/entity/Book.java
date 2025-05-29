package mts.parser.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "book")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String rating;
    private String votes;
    private String pageUrl;
}