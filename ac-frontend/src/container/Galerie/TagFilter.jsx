import React, { useState } from 'react';

const TagFilter = ({ tags, onFilter }) => {
  const [selectedTags, setSelectedTags] = useState([]);

  const handleTagClick = (tag) => {
    if (selectedTags.includes(tag)) {
      setSelectedTags(selectedTags.filter((t) => t !== tag));
    } else {
      setSelectedTags([...selectedTags, tag]);
    }
  };

  const handleClearTags = () => {
    setSelectedTags([]);
  };

  const isTagSelected = (tag) => selectedTags.includes(tag);

  return (
    <div>
      <h3>Tags:</h3>
      <div>
        {tags.map((tag) => (
          <button
            key={tag}
            onClick={() => handleTagClick(tag)}
            className={isTagSelected(tag) ? 'selected' : ''}
          >
            {tag}
          </button>
        ))}
      </div>
      <button onClick={handleClearTags}>Clear Tags</button>
    </div>
  );
};

export default TagFilter;
