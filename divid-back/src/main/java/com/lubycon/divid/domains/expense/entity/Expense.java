package com.lubycon.divid.domains.expense.entity;

import com.lubycon.divid.domains.trip.entity.Trip;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @Type(type = "uuid-char")
    @Column(name = "trip_id", nullable = false)
    private UUID tripId;

    @Column(name = "payer_id", nullable = false)
    private long payerId;

    @Column(name = "pay_date", nullable = false)
    private LocalDate payDate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

    @Column(name = "individual", nullable = false)
    private boolean individual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false, insertable = false, updatable = false)
    private Trip trip;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseDetail> expenseDetails = new ArrayList<>();

    public void updateExpense(long payerId, LocalDate payDate, String title, long totalPrice) {
        this.payerId = payerId;
        this.payDate = payDate;
        this.title = title;
        this.totalPrice = totalPrice;
    }

    @Builder
    public Expense(UUID tripId, long payerId, LocalDate payDate, String title, long totalPrice, boolean individual) {
        this.tripId = tripId;
        this.payerId = payerId;
        this.payDate = payDate;
        this.title = title;
        this.totalPrice = totalPrice;
        this.individual = individual;
    }

}
