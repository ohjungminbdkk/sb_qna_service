package com.sbs.qna_service.boundedContext.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sbs.qna_service.boundedContext.answer.Answer;
import com.sbs.qna_service.boundedContext.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Question {
	@Id // PKEY
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private Integer id; // 質問データの固有番号

	@Column(length = 200) // VARCHAR(200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content; // 内容

	private LocalDateTime createDate;
	
    private LocalDateTime modifyDate;
	
    @ManyToOne // 아래 모델에서 외래키만 저장이됨.
    // FetchType.EAGER로 설정하면 author 필드에 저장된 userId뿐만 아니라 전체 사용자 정보도 함께 가져옴.
    // 그러나 모든 관계를 EAGER로 설정하면 성능 저하 가능성이 있음, 따라서 필요할 때만 사용해야 함.
    private SiteUser author;

	// CascadeType.REMOVE 質問が削除されたら、コメントも削除される。
	// これは、DBテーブルにはカラムが生成されない。（DBはリストや配列ができない。）
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList = new ArrayList<>();

	//外部にてanswerListファイルに接近することを遮断
	public void addAnswer(Answer a) {
		a.setQuestion(this);
		answerList.add(a);
	}
}