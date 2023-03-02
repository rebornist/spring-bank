# 은행 앱 개발 및 Junit 활용 실습

- 인프런을 통해 VSCode에서 Spring boot 활용법 스터디
- Spring boot security를 이용한 다양한 인증기능 구현


### Jpa LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main Class)
- @EntityListeners(AuditingEntityListener.class) (Entity Class)
```java
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
```