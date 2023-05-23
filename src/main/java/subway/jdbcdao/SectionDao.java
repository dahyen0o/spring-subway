package subway.jdbcdao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.domain.entity.Section;
import subway.domain.repository.SectionRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SectionDao implements SectionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    private RowMapper<Section> rowMapper = (rs, rowNum) ->
            new Section(
                    rs.getLong("id"),
                    rs.getLong("down_station_id"),
                    rs.getLong("up_station_id"),
                    rs.getLong("line_id"),
                    rs.getInt("distance")
            );

    public SectionDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("section")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Section insert(Section section) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(section);
        Long id = insertAction.executeAndReturnKey(params).longValue();
        return new Section(id, section.getUpStationId(), section.getDownStationId(), section.getLineId(), section.getDistance());
    }

    @Override
    public List<Section> findAllByLineId(Long lineId) {
        String sql = "select * from SECTION where line_id = ?";
        return jdbcTemplate.query(sql, rowMapper, lineId);
    }

    @Override
    public void deleteByStationId(Long lineId, Long stationId) {
        jdbcTemplate.update("delete from SECTION where line_id = ? and down_station_id = ?", lineId, stationId);
    }
}
