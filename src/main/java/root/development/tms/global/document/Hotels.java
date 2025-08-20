package root.development.tms.global.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class Hotels extends BaseDocument{

    @Id
    private String id;

    private String name;
    private String phoneNumber;
    private String imageUrl;
    private String description;
    private String placeId;
    private List<RoomType> roomTypes;
    private List<MealPlan> mealPlans;

    @Getter
    @Setter
    public static class RoomType{
        private String roomTypeName;
        private Double price;
    }

    @Getter
    @Setter
    public static class MealPlan{
        private String mealPlanName;
        private Double price;
    }

}