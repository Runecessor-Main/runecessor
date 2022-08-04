package game.content.runehub.entity.player;

import org.rhd.api.io.db.DatabaseAcessManager;
import org.rhd.api.io.db.Query;
import org.rhd.api.io.db.QueryParameter;
import org.rhd.api.io.db.SqlDataType;
import org.rhd.api.io.db.impl.AbstractDataAcessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerCharacterContextDataAccessObject extends AbstractDataAcessObject<PlayerCharacterContext> {

    private static final String TABLE_NAME = "player_context";

    private static PlayerCharacterContextDataAccessObject instance = null;

    public static PlayerCharacterContextDataAccessObject getInstance() {
        if (instance == null)
            instance = new PlayerCharacterContextDataAccessObject();
        return instance;
    }

    @Override
    public List<PlayerCharacterContext> getAllEntries() {
        final List<PlayerCharacterContext> items = new ArrayList<>();
        final Query query = new Query.QueryBuilder()
                .addQuery("SELECT * FROM")
                .addQuery(TABLE_NAME)
                .build();
        try (Connection conn = DatabaseAcessManager.getInstance().connect(this.getDatabaseServiceProvider().getUrl());
             PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(query.getSql())) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                items.add(
                        new PlayerCharacterContext.PlayerCharacterContextBuilder(rs.getLong("id"))
                                .withPlayerSaveData(rs.getString("playerSaveData"))
                                .build()
                );
            }
            return items;
        } catch (SQLException e) {
            System.out.println("CUSTOM: " + e.getMessage());
        }
        return items;
    }

    @Override
    public void update(PlayerCharacterContext context) {
        try {
            final Query query = new Query.QueryBuilder().addQuery("UPDATE")
                    .addQuery(TABLE_NAME)
                    .addQuery("SET "
                            + "playerSaveData='" + new PlayerSaveDataSerializer().serialize(context.getPlayerSaveData()) + "'"
                    )

                    .addQuery("WHERE id='" + context.getId() + "'")
                    .build();
            try (Connection conn = DatabaseAcessManager.getInstance().connect(this.getDatabaseServiceProvider().getUrl());
                 PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(query.getSql())) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("PROBLEM");
                if (this.getCachedEntries().containsKey(context.getId())) {
                    this.delete(context);
                }
                this.create(context);
            }
            if (!this.getCachedEntries().containsKey(context.getId())) {
                this.getCachedEntries().putIfAbsent(context.getId(), context);
            } else {
                this.getCachedEntries().replace(context.getId(), context);
            }
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void create(PlayerCharacterContext context) {
        final Query query = new Query.QueryBuilder().addQuery("INSERT INTO")
                .addQuery(TABLE_NAME)
                .addQuery("(id,playerSaveData)")
                .addQuery("VALUES(?,?)")
                .build();
        try (Connection conn = DatabaseAcessManager.getInstance().connect(this.getDatabaseServiceProvider().getUrl());
             PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(query.getSql())) {
            pstmt.setLong(1, context.getId());
            pstmt.setString(2, new PlayerSaveDataSerializer().serialize(context.getPlayerSaveData()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (!this.getCachedEntries().containsKey(context.getId())) {
            this.getCachedEntries().putIfAbsent(context.getId(), context);
        } else {
            this.getCachedEntries().replace(context.getId(), context);
        }
    }

    @Override
    public void delete(PlayerCharacterContext context) {
        final Query query = new Query.QueryBuilder()
                .addQuery("DELETE FROM")
                .addQuery(TABLE_NAME)
                .addQuery("WHERE id='" + context.getId() + "'")
                .build();
        try (Connection conn = DatabaseAcessManager.getInstance().connect(this.getDatabaseServiceProvider().getUrl());
             PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(query.getSql())) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.getCachedEntries().remove(context.getId());
    }

    @Override
    public PlayerCharacterContext read(PlayerCharacterContext context) {
        return this.read(context.getId());
    }

    @Override
    public PlayerCharacterContext read(long l) {
        System.out.println("Fucking read");
        System.out.println("READING: " + l);
            final Query query = new Query.QueryBuilder()
                    .addQuery("SELECT * FROM")
                    .addQuery(TABLE_NAME)
                    .addQuery("WHERE id=" + l)
                    .build();
            try (Connection conn = DatabaseAcessManager.getInstance().connect(this.getDatabaseServiceProvider().getUrl());
                 PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(query.getSql())) {
                ResultSet rs = pstmt.executeQuery();
                PlayerCharacterContext.PlayerCharacterContextBuilder builder = new PlayerCharacterContext.PlayerCharacterContextBuilder(rs.getLong("id"));
                while (rs.next()) {
                    builder.withPlayerSaveData(rs.getString("playerSaveData"));
                }
                return builder.build();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (IndexOutOfBoundsException e) {

            }
        final PlayerCharacterContext ctx = new PlayerCharacterContext.PlayerCharacterContextBuilder(l)
                .build();
        this.create(ctx);
        return ctx;
    }

    private PlayerCharacterContextDataAccessObject() {
        super("./data/runehub/db/players.db");
        this.getDatabaseServiceProvider().createTable(TABLE_NAME,
                new QueryParameter("id", SqlDataType.BIGINT, QueryParameter.PRIMARY_KEY),
                new QueryParameter("playerSaveData", SqlDataType.LONGTEXT)
        );
    }
}
