package dev.nieves.export;

import dev.nieves.model.Moment;
import java.io.IOException;
import java.util.List;

/**
 * Contrato para exportar una lista de momentos vividos a un formato externo.
 */
public interface InterfaceMomentExporter {

    String export(List<Moment> moments) throws IOException;
}
