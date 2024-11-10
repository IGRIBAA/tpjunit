package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}
	@Test
	//S3: N'imprine pas si pas assez d'argent
	void nImprimePasSiBalanceInsuffisante(){
		//GIVEN: une machine vierge (initialisée dans @BeforeEach)
		//WHEN on ne met pas assez d'argent
		machine.insertMoney( PRICE-1);
		//THEN ça n'imprime pas
		assertFalse(machine.printTicket(),"Pas assez d'argent, on ne doit pas imprimer");
	}
	@Test
		//S4: imprine si assez d'argent
	void ImprimePasSiBalanceInsuffisante(){
		//GIVEN: une machine vierge (initialisée dans @BeforeEach)
		//WHEN on ne met pas assez d'argent
		machine.insertMoney( PRICE);
		//THEN ça n'imprime pas
		assertTrue(machine.printTicket(),"il y a assez d'argent, on doit imprimer");
	}

	@Test
		//S5: Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void DecrementePrixTicket(){
		// GIVEN: une machine avec suffisamment d'argent
		machine.insertMoney(PRICE); // Insérer exactement le prix du ticket
		// WHEN on imprime le ticket
		boolean result = machine.printTicket();
		// THEN ça imprime le ticket et la balance est décrémentée du prix
		assertTrue(result, "Le ticket doit être imprimé");
		assertEquals(0, machine.getBalance(), "La balance doit être à zéro après l'achat");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void mettreAJourApresImpression(){
		machine.insertMoney(PRICE);
		int prix1 = machine.getTotal();
		machine.printTicket(PRICE);
		assertEquals(machine.getTotal(), prix1+PRICE, "Operation incorrecte");
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	void refundRendCorrectementMonnaie(){
		machine.insertMoney(PRICE+10);
		machine.printTicket(PRICE);
		// int prix2 = machine.getBalance();
		int monnaieRendue = machine.refund();
		assertEquals(monnaieRendue/*prix2 */, 10, "Monnaie non rendu correctement");
	}

	@Test
	// S8 : refund() remet la balance à zéro
	void refundRendBalanceAZero(){
		machine.insertMoney(PRICE);
		machine.refund();
		int refund = machine.getBalance();
		assertEquals(refund, 0, "Operation non aboutie correctement");
	}

	@Test
	// S9 : On ne peut pas insérer un montant négatif
	void verifierMontant(){
		int valeurNegatif = -1 * PRICE;
		assertFalse(machine.insertMoney(valeurNegatif), "Operation ne peut pas aboutir");
	}

	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void verifierPrixTicket() {
		Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-PRICE));

		assertEquals("Ticket price must be positive", exception.getMessage(), "L'exception n'a pas le message attendu");
	}



}
