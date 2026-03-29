package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectContainer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.events.EventRegistry;
import com.db4o.events.EventRegistryFactory;
import com.db4o.query.Query;

public class ControleID {

    // banco de controle de ids
    private static ObjectContainer sequencia;

    public static void ativar(ObjectContainer manager) {

        if (manager == null)
            throw new RuntimeException("manager nulo");

        // define tipo de banco (local/remoto)
        if (manager instanceof EmbeddedObjectContainer) {
            sequencia = Db4oEmbedded.openFile(
                    Db4oEmbedded.newConfiguration(), "sequencia.db4o");
        } else {
            String ip = Util.getIPservidor();
            sequencia = Db4oClientServer.openClient(
                    Db4oClientServer.newClientConfiguration(),
                    ip, 35000, "usuario0", "senha0");
        }

        EventRegistry eventos = EventRegistryFactory.forObjectContainer(manager);

        // antes de salvar objeto
        eventos.creating().addListener((e, args) -> {
            try {
                Object obj = args.object();

                Field campo = null;

                // procura campo "id"
                for (Field f : getAllFieldsList(obj.getClass())) {
                    if (f.getName().equals("id")) {
                        campo = f;
                        break;
                    }
                }

                if (campo != null) {

                    Class<?> cls = obj.getClass();
                    String nomeClasse;

                    // trata herança
                    if (cls.getSuperclass() == Object.class)
                        nomeClasse = cls.getName();
                    else
                        nomeClasse = cls.getSuperclass().getName();

                    RegistroID reg = lerRegistroID(nomeClasse);

                    reg.incrementarID(); // +1 id

                    campo.setAccessible(true);
                    campo.setInt(obj, reg.getId());

                    sequencia.store(reg);
                    sequencia.commit();
                }

            } catch (Exception ex) {
            }
        });

        // após commit limpa cache
        eventos.created().addListener((e, args) -> {
            manager.ext().purge();
            sequencia.ext().purge();
        });

        // antes de fechar
        eventos.closing().addListener((e, args) -> {
            if (sequencia != null && !sequencia.ext().isClosed())
                sequencia.close();
        });

        // após query limpa cache
        eventos.queryFinished().addListener((e, args) -> {
            manager.ext().purge();
            sequencia.ext().purge();
        });
    }

    // busca registro da classe
    private static RegistroID lerRegistroID(String nomeClasse) {
        Query q = sequencia.query();
        q.constrain(RegistroID.class);
        q.descend("nomeClasse").constrain(nomeClasse);

        List<RegistroID> lista = q.execute();

        if (!lista.isEmpty())
            return lista.get(0);

        return new RegistroID(nomeClasse);
    }

    // resetar id manual
    public static void resetarRegistroID(Class<?> classe, int valor) {
        String nomeClasse = classe.getName();

        RegistroID reg = lerRegistroID(nomeClasse);

        if (reg != null)
            sequencia.delete(reg);

        reg = new RegistroID(nomeClasse, valor);

        sequencia.store(reg);
        sequencia.commit();
    }

    // pega todos campos (inclui herança)
    public static <X> List<Field> getAllFieldsList(Class<X> cls) {
        List<Field> lista = new ArrayList<>();

        Class<?> atual = cls;

        while (atual != null) {
            Collections.addAll(lista, atual.getDeclaredFields());
            atual = atual.getSuperclass();
        }

        return lista;
    }
}

class RegistroID {

    private String nomeClasse;
    private int ultimoId;

    public RegistroID(String nomeClasse) {
        this.nomeClasse = nomeClasse;
        this.ultimoId = 0;
    }

    public RegistroID(String nomeClasse, int valorInicial) {
        this.nomeClasse = nomeClasse;
        this.ultimoId = valorInicial;
    }

    public int getId() {
        return ultimoId;
    }

    public void incrementarID() {
        ultimoId++;
    }

    @Override
    public String toString() {
        return "Classe=" + nomeClasse + " ID=" + ultimoId;
    }
}