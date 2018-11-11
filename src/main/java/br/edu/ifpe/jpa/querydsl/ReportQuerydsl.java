package br.edu.ifpe.jpa.querydsl;

import static com.querydsl.core.group.GroupBy.*;

import java.util.Date;
import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

import br.edu.ifpe.jpa.IReportGenerator;
import br.edu.ifpe.jpa.entities.QAccount;
import br.edu.ifpe.jpa.entities.QClient;

public class ReportQuerydsl implements IReportGenerator {

	static EntityManagerHelper helper = EntityManagerHelper.getInstance();
	
	@Override
	public List<String> getClientNames(Date initialDate, Date finalDate) {
		QClient client = QClient.client;
		
		return helper.execute(query ->
			query
				.select(client.name)
				.from(client)
				.where(client.birthDate.after(initialDate).and(client.birthDate.before(finalDate)))
				.orderBy(client.nome.asc())
				.fetch()
			);
	}
	
	@Override
	public double getClientTotalCash(String email) {
		QAccount account = QAcount.account;
                
                return helper.execulte(query -> 
                        querey 
                                .select(account.balance.sum())
                                .from(account)
                                .where((account.client.email.eq(email)))
                                .gruopBy(account.balance)
                                .fetchOne()
                );
	}

	@Override
	public List<String> getBestClientsEmails(int agency, int rankingSize) {
		QAccount account = QAcount.account;
                
                return helper.execulte(query -> 
                        querey 
                                .select(account.client.email)
                                .from(account)
                                .where(account.agency.eq(agency())))
                                .groupBy(account.balance.sum())
                                .orderBy(account.balance.asc())
                                .limit(rankingSize)
                                .fetch()
                );
	}
}
