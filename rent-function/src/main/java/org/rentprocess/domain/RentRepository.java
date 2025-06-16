package org.rentprocess.domain;

import com.microsoft.azure.functions.ExecutionContext;
import org.rentprocess.domain.model.RentModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class RentRepository {

    public Optional<RentModel> saveRent(RentModel rentModel, ExecutionContext context) throws SQLException {
        String connectionString = System.getenv("SQL_CONNECTION_STRING");

        try (Connection connection = DriverManager.getConnection(connectionString)) {
            connection.setAutoCommit(false);

            String sql = """
                    INSERT INTO Locacao (Nome, Email, Modelo, Ano, TempoAluguel, Data)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, rentModel.getNome());
            statement.setString(2, rentModel.getEmail());
            statement.setString(3, rentModel.getModelo());
            statement.setInt(4, rentModel.getAno());
            statement.setString(5, rentModel.getTempoAluguel());
            statement.setObject(6, rentModel.getData());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                context.getLogger().info("LOCACAO SALVA NO BANCO");
                return Optional.of(rentModel);
            }

            connection.rollback();
            context.getLogger().info("FALHA AO SALVAR LOCACAO");
            return Optional.empty();
        }
    }
}
