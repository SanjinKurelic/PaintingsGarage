import React from 'react'
import './searchFilter.scss'
import {VscChromeClose} from 'react-icons/all'
import PropTypes from 'prop-types'

const SearchFilter = ({authors, setAuthors, hashtags, setHashtags}) => {
  const removeAuthor = (id) => {
    setAuthors(authors.filter((a) => a.id !== id))
  }

  const removeHashtag = (id) => {
    setHashtags(hashtags.filter((h) => h.id !== id))
  }

  const Filter = ({item, removeItem}) => {
    return (
      <div className="d-inline-block mx-1 search-filter rounded-1"><span
        className="search-filter-content p-1 d-inline-block rounded-start">{item.value}</span><VscChromeClose
        className="search-filter-clear p-1" onClick={() => removeItem(item.id)}/></div>
    )
  }

  return (
    <>
      {(authors.length > 0 || hashtags.length > 0) && <div className="search-filters mt-1">
        {authors.map((author) => <Filter key={'@' + author.id} item={author} removeItem={removeAuthor}/>)}
        {hashtags.map((hashtag) => <Filter key={'#' + hashtag.id} item={hashtag} removeItem={removeHashtag}/>)}
      </div>}
    </>
  )
}

SearchFilter.propTypes = {
  setAuthors: PropTypes.func.isRequired,
  setHashtags: PropTypes.func.isRequired,
  authors: PropTypes.array,
  hashtags: PropTypes.array
}

export default SearchFilter
