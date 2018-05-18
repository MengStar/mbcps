package meng.xing.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public final class Quote {

    @Indexed
    private Long seq;
    private String book;
    private String content;

    @Override
    public String toString() {
        return "Quote{" +
                "seq='" + seq + '\'' +
                ", book='" + book + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (!seq.equals(quote.getSeq())) return false;
        if (book != null ? !book.equals(quote.book) : quote.book != null) return false;
        return content != null ? content.equals(quote.content) : quote.content == null;
    }

    @Override
    public int hashCode() {
        int result = seq.hashCode();
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}

