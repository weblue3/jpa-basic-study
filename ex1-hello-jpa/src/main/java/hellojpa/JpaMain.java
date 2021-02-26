package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //-- 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //-- 엔티티 매니저는 쓰레드간에 공유X(사용하고 버려야 한다.)
        EntityManager em = emf.createEntityManager();

        //-- JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            //-- Member findMember = em.find(Member.class, 1L);
            //-- findMember.setName("HelloJPA");

            //-- JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for(Member member : result){
                System.out.println("member.name = " + member.getName());
            }

            //-- 삭제 기능
            //-- em.remove(findMember);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
