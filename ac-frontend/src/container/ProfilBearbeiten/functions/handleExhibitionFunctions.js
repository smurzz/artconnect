function handleExhibition (index, element, exhibitionValues)  {
    let newExhibition = [...exhibitionValues];
    newExhibition[index][element.target.name] = element.target.value;
    return newExhibition;
}
function addExhibitionFields (exhibitionValues) {
    return [...exhibitionValues, {title: "", location: "", year: "", description: ""}]
}
function removeExhibitionFields (index, exhibitionValues) {
    let newExhibitions = [...exhibitionValues];
    newExhibitions.splice(index, 1);
    return newExhibitions
}

export const ExhibitionService = {
    handleExhibition,
    addExhibitionFields,
    removeExhibitionFields
}